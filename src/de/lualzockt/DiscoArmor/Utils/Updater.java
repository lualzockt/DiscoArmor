package de.lualzockt.DiscoArmor.Utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import de.lualzockt.DiscoArmor.DiscoArmor;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;


public class Updater
{
  private Plugin plugin;
  private UpdateType type;
  private String versionName;
  private String versionLink;
  private String versionType;
  private String versionGameVersion;
  private boolean announce;
  private URL url;
  private File file;
  private Thread thread;
  private int id = -1;
  private String apiKey = null;
  private static final String TITLE_VALUE = "name";
  private static final String LINK_VALUE = "downloadUrl";
  private static final String TYPE_VALUE = "releaseType";
  private static final String VERSION_VALUE = "gameVersion";
  private static final String QUERY = "/servermods/files?projectIds=";
  private static final String HOST = "https://api.curseforge.com";
  private static final String[] NO_UPDATE_TAG = { "-DEV", "-PRE", "-SNAPSHOT", "-BETA" };
  private static final int BYTE_SIZE = 1024;
  private YamlConfiguration config;
  private String updateFolder;
  private UpdateResult result = UpdateResult.SUCCESS;
  private BufferedInputStream bis;
private File destinationFilePath;
  public Updater(Plugin plugin, int id, File file, UpdateType type, boolean announce)
  {
    this.plugin = plugin;
    this.type = type;
    this.announce = announce;
    this.file = file;
    this.id = id;
    this.updateFolder = plugin.getServer().getUpdateFolder();

    File pluginFile = plugin.getDataFolder().getParentFile();
    File updaterFile = new File(pluginFile, "Updater");
    File updaterConfigFile = new File(updaterFile, "config.yml");

    if (!updaterFile.exists()) {
      updaterFile.mkdir();
    }
    if (!updaterConfigFile.exists()) {
      try {
        updaterConfigFile.createNewFile();
      } catch (IOException e) {
        plugin.getLogger().severe("The updater could not create a configuration in " + updaterFile.getAbsolutePath());
        e.printStackTrace();
      }
    }
    this.config = YamlConfiguration.loadConfiguration(updaterConfigFile);

    this.config.options().header("This configuration file affects all plugins using the Updater system (version 2+ - http://forums.bukkit.org/threads/96681/ )\nIf you wish to use your API key, read http://wiki.bukkit.org/ServerMods_API and place it below.\nSome updating systems will not adhere to the disabled value, but these may be turned off in their plugin's configuration.");

    this.config.addDefault("api-key", "PUT_API_KEY_HERE");
    this.config.addDefault("disable", Boolean.valueOf(false));

    if (this.config.get("api-key", null) == null) {
      this.config.options().copyDefaults(true);
      try {
        this.config.save(updaterConfigFile);
      } catch (IOException e) {
        plugin.getLogger().severe("The updater could not save the configuration in " + updaterFile.getAbsolutePath());
        e.printStackTrace();
      }
    }

    if (this.config.getBoolean("disable")) {
      this.result = UpdateResult.DISABLED;
      return;
    }

    String key = this.config.getString("api-key");
    if ((key.equalsIgnoreCase("PUT_API_KEY_HERE")) || (key.equals(""))) {
      key = null;
    }

    this.apiKey = key;
    try
    {
      this.url = new URL("https://api.curseforge.com/servermods/files?projectIds=" + id);
    } catch (MalformedURLException e) {
      plugin.getLogger().severe("The project ID provided for updating, " + id + " is invalid.");
      this.result = UpdateResult.FAIL_BADID;
      e.printStackTrace();
    }

    this.thread = new Thread(new UpdateRunnable(null));
    this.thread.start();
  }
  public Updater(Plugin plugin, int id, File file, UpdateType type, boolean announce, CommandSender cs) {
    this.plugin = plugin;
    this.type = type;
    this.announce = announce;
    this.file = file;
    this.id = id;
    this.updateFolder = plugin.getServer().getUpdateFolder();

    File pluginFile = plugin.getDataFolder().getParentFile();
    File updaterFile = new File(pluginFile, "Updater");
    File updaterConfigFile = new File(updaterFile, "config.yml");

    if (!updaterFile.exists()) {
      updaterFile.mkdir();
    }
    if (!updaterConfigFile.exists()) {
      try {
        updaterConfigFile.createNewFile();
      } catch (IOException e) {
        plugin.getLogger().severe("The updater could not create a configuration in " + updaterFile.getAbsolutePath());
        e.printStackTrace();
      }
    }
    this.config = YamlConfiguration.loadConfiguration(updaterConfigFile);

    this.config.options().header("This configuration file affects all plugins using the Updater system (version 2+ - http://forums.bukkit.org/threads/96681/ )\nIf you wish to use your API key, read http://wiki.bukkit.org/ServerMods_API and place it below.\nSome updating systems will not adhere to the disabled value, but these may be turned off in their plugin's configuration.");

    this.config.addDefault("api-key", "PUT_API_KEY_HERE");
    this.config.addDefault("disable", Boolean.valueOf(false));

    if (this.config.get("api-key", null) == null) {
      this.config.options().copyDefaults(true);
      try {
        this.config.save(updaterConfigFile);
      } catch (IOException e) {
        plugin.getLogger().severe("The updater could not save the configuration in " + updaterFile.getAbsolutePath());
        e.printStackTrace();
      }
    }

    if (this.config.getBoolean("disable")) {
      this.result = UpdateResult.DISABLED;
      return;
    }

    String key = this.config.getString("api-key");
    if ((key.equalsIgnoreCase("PUT_API_KEY_HERE")) || (key.equals(""))) {
      key = null;
    }

    this.apiKey = key;
    try
    {
      this.url = new URL("https://api.curseforge.com/servermods/files?projectIds=" + id);
    } catch (MalformedURLException e) {
      plugin.getLogger().severe("The project ID provided for updating, " + id + " is invalid.");
      this.result = UpdateResult.FAIL_BADID;
      e.printStackTrace();
    }

    this.thread = new Thread(new UpdateRunnable(cs));
    this.thread.start();
  }

  public UpdateResult getResult()
  {
    waitForThread();
    return this.result;
  }

  public String getLatestType()
  {
    waitForThread();
    return this.versionType;
  }

  public String getLatestGameVersion()
  {
    waitForThread();
    return this.versionGameVersion;
  }

  public String getLatestName()
  {
    waitForThread();
    return this.versionName;
  }

  public String getLatestFileLink()
  {
    waitForThread();
    return this.versionLink;
  }

  private void waitForThread()
  {
    if ((this.thread != null) && (this.thread.isAlive()))
      try {
        this.thread.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
  }

  private void saveFile(File folder, String file, String u)
  {
    if (!folder.exists()) {
      folder.mkdir();
    }
    BufferedInputStream in = null;
    FileOutputStream fout = null;
    try
    {
      URL url = new URL(u);
      int fileLength = url.openConnection().getContentLength();
      in = new BufferedInputStream(url.openStream());
      fout = new FileOutputStream(folder.getAbsolutePath() + "/" + file);

      byte[] data = new byte[1024];

      if (this.announce) {
        this.plugin.getLogger().info("About to download a new update: " + this.versionName);
      }
      long downloaded = 0L;
      int count;
      while ((count = in.read(data, 0, 1024)) != -1){
        downloaded += count;
        fout.write(data, 0, count);
        int percent = (int)(downloaded * 100L / fileLength);
        if ((this.announce) && (percent % 10 == 0)) {
          this.plugin.getLogger().info("Downloading update: " + percent + "% of " + fileLength + " bytes.");
        }
      }

      for (File xFile : new File(this.plugin.getDataFolder().getParent(), this.updateFolder).listFiles()) {
        if (xFile.getName().endsWith(".zip")) {
          xFile.delete();
        }
      }

      File dFile = new File(folder.getAbsolutePath() + "/" + file);
      if (dFile.getName().endsWith(".zip"))
      {
        unzip(dFile.getCanonicalPath());
      }
      if (this.announce)
        this.plugin.getLogger().info("Finished updating.");
    }
    catch (Exception ex) {
      this.plugin.getLogger().warning("The auto-updater tried to download a new update, but was unsuccessful.");
      this.result = UpdateResult.FAIL_DOWNLOAD;
      try
      {
        if (in != null) {
          in.close();
        }
        if (fout != null)
          fout.close();
      }
      catch (Exception localException1)
      {
      }
    }
    finally
    {
      try
      {
        if (in != null) {
          in.close();
        }
        if (fout != null)
          fout.close();
      }
      catch (Exception localException2)
      {
      }
    }
  }

  private void unzip(String file) {
      try {
          final File fSourceZip = new File(file);
          final String zipPath = file.substring(0, file.length() - 4);
          ZipFile zipFile = new ZipFile(fSourceZip);
          Enumeration<? extends ZipEntry> e = zipFile.entries();
          while (e.hasMoreElements()) {
              ZipEntry entry = e.nextElement();
              File destinationFilePath = new File(zipPath, entry.getName());
              destinationFilePath.getParentFile().mkdirs();
              if (entry.isDirectory()) {
                  continue;
              } else {
                  final BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(entry));
                  int b;
                  final byte buffer[] = new byte[Updater.BYTE_SIZE];
                  final FileOutputStream fos = new FileOutputStream(destinationFilePath);
                  final BufferedOutputStream bos = new BufferedOutputStream(fos, Updater.BYTE_SIZE);
                  while ((b = bis.read(buffer, 0, Updater.BYTE_SIZE)) != -1) {
                      bos.write(buffer, 0, b);
                  }
                  bos.flush();
                  bos.close();
                  bis.close();
                  final String name = destinationFilePath.getName();
                  if (name.endsWith(".jar") && this.pluginFile(name)) {
                      destinationFilePath.renameTo(new File(this.plugin.getDataFolder().getParent(), this.updateFolder + File.separator + name));
                  }
              }
              entry = null;
              destinationFilePath = null;
          }
          e = null;
          zipFile.close();
          zipFile = null;

          // Move any plugin data folders that were included to the right place, Bukkit won't do this for us.
          for (final File dFile : new File(zipPath).listFiles()) {
              if (dFile.isDirectory()) {
                  if (this.pluginFile(dFile.getName())) {
                      final File oFile = new File(this.plugin.getDataFolder().getParent(), dFile.getName()); // Get current dir
                      final File[] contents = oFile.listFiles(); // List of existing files in the current dir
                      for (final File cFile : dFile.listFiles()) // Loop through all the files in the new dir
                      {
                          boolean found = false;
                          for (final File xFile : contents) // Loop through contents to see if it exists
                          {
                              if (xFile.getName().equals(cFile.getName())) {
                                  found = true;
                                  break;
                              }
                          }
                          if (!found) {
                              // Move the new file into the current dir
                              cFile.renameTo(new File(oFile.getCanonicalFile() + File.separator + cFile.getName()));
                          } else {
                              // This file already exists, so we don't need it anymore.
                              cFile.delete();
                          }
                      }
                  }
              }
              dFile.delete();
          }
          new File(zipPath).delete();
          fSourceZip.delete();
      } catch (final IOException e) {
          this.plugin.getLogger().log(Level.SEVERE, "The auto-updater tried to unzip a new update file, but was unsuccessful.", e);
          this.result = Updater.UpdateResult.FAIL_DOWNLOAD;
      }
      new File(file).delete();
  }

  /**
   * Check if the name of a jar is one of the plugins currently installed, used for extracting the correct files out of a zip.
   *
   * @param name a name to check for inside the plugins folder.
   * @return true if a file inside the plugins folder is named this.
   */

  private boolean pluginFile(String name){
    for (File file : new File("plugins").listFiles()) {
      if (file.getName().equals(name)) {
        return true;
      }
    }
    return false;
  }

  private boolean versionCheck(String title){
    if (this.type != UpdateType.NO_VERSION_CHECK) {
      String version = this.plugin.getDescription().getVersion();
      if (title.split(" ").length == 2) {
        String remoteVersion = title.split(" ")[1];

        if ((hasTag(version)) || (version.equalsIgnoreCase(remoteVersion)))
        {
          this.result = UpdateResult.NO_UPDATE;
          return false;
        }
      }
      else {
        String authorInfo = " (" + (String)this.plugin.getDescription().getAuthors().get(0) + ")";
        this.plugin.getLogger().warning("The author of this plugin" + authorInfo + " has misconfigured their Auto Update system");
        this.plugin.getLogger().warning("File versions should follow the format 'PluginName vVERSION'");
        this.plugin.getLogger().warning("Please notify the author of this error.");
        this.result = UpdateResult.FAIL_NOVERSION;
        return false;
      }
    }
    return true;
  }

  private boolean hasTag(String version){
    for (String string : NO_UPDATE_TAG) {
      if (version.contains(string)) {
        return true;
      }
    }
    return false;
  }

  private boolean read() {
    try {
      URLConnection conn = this.url.openConnection();
      conn.setConnectTimeout(5000);

      if (this.apiKey != null) {
        conn.addRequestProperty("X-API-Key", this.apiKey);
      }
      conn.addRequestProperty("User-Agent", "Updater (by Gravity)");

      conn.setDoOutput(true);

      BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      String response = reader.readLine();

      JSONArray array = (JSONArray)JSONValue.parse(response);

      if (array.size() == 0) {
        this.plugin.getLogger().warning("The updater could not find any files for the project id " + this.id);
        this.result = UpdateResult.FAIL_BADID;
        return false;
      }

      this.versionName = ((String)((JSONObject)array.get(array.size() - 1)).get("name"));
      this.versionLink = ((String)((JSONObject)array.get(array.size() - 1)).get("downloadUrl"));
      this.versionType = ((String)((JSONObject)array.get(array.size() - 1)).get("releaseType"));
      this.versionGameVersion = ((String)((JSONObject)array.get(array.size() - 1)).get("gameVersion"));

      return true;
    } catch (IOException e) {
      if (e.getMessage().contains("HTTP response code: 403")) {
        this.plugin.getLogger().warning("dev.bukkit.org rejected the API key provided in plugins/Updater/config.yml");
        this.plugin.getLogger().warning("Please double-check your configuration to ensure it is correct.");
        this.result = UpdateResult.FAIL_APIKEY;
      } else {
        this.plugin.getLogger().warning("The updater could not contact dev.bukkit.org for updating.");
        this.plugin.getLogger().warning("If you have not recently modified your configuration and this is the first time you are seeing this message, the site may be experiencing temporary downtime.");
        this.result = UpdateResult.FAIL_DBO;
      }
      e.printStackTrace();
    }return false;
  }

  public static enum UpdateResult
  {
    SUCCESS, 

    NO_UPDATE, 

    DISABLED, 

    FAIL_DOWNLOAD, 

    FAIL_DBO, 

    FAIL_NOVERSION, 

    FAIL_BADID, 

    FAIL_APIKEY, 

    UPDATE_AVAILABLE;
  }

  private class UpdateRunnable implements Runnable{
    private CommandSender cs;

    public UpdateRunnable(CommandSender cs)
    {
      this.cs = cs;
    }

    public void run() {
      if (Updater.this.url != null)
      {
        if ((Updater.this.read()) && 
          (Updater.this.versionCheck(Updater.this.versionName))) {
          if ((Updater.this.versionLink != null) && (Updater.this.type != Updater.UpdateType.NO_DOWNLOAD)) {
            String name = Updater.this.file.getName();

            if (Updater.this.versionLink.endsWith(".zip")) {
              String[] split = Updater.this.versionLink.split("/");
              name = split[(split.length - 1)];
            }
            Updater.this.saveFile(new File(Updater.this.plugin.getDataFolder().getParent(), Updater.this.updateFolder), name, Updater.this.versionLink);
          } else {
            Updater.this.result = Updater.UpdateResult.UPDATE_AVAILABLE;
          }
          DiscoArmor.getInstance().updateNeeded = true;
          DiscoArmor.getInstance().version = Updater.this.versionName;
          DiscoArmor.getInstance().download = Updater.this.versionLink;
          if (this.cs != null)
            this.cs.sendMessage("§8[§cDiscoArmor§8]A new Update is avaible! Download the newest Verion (" + Updater.this.versionName + ") here: " + Updater.this.versionLink);
        }
      }
    }
  }

  public static enum UpdateType
  {
    DEFAULT, 

    NO_VERSION_CHECK, 

    NO_DOWNLOAD;
  }
}