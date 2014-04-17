package de.lualzockt.DiscoArmor;

public final class EngineMode
{
  public static final int RGB_RANDOM = 0;
  public static final int DEFINED_COLORS_RANDOM = 1;

  public static boolean isValid(int type)
  {
    return (type == 1) | (type == 0);
  }
}