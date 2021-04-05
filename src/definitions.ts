declare module '@capacitor/core' {
  interface PluginRegistry {
    TGDWallpaperManager: TGDWallpaperManagerPlugin;
  }
}

export interface TGDWallpaperManagerPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
  applyWallpaper(options: { value: string }): Promise<{ value: string }>;
}
