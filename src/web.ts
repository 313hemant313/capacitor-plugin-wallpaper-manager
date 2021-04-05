import { WebPlugin } from '@capacitor/core';
import { TGDWallpaperManagerPlugin } from './definitions';

export class TGDWallpaperManagerWeb extends WebPlugin implements TGDWallpaperManagerPlugin {
  constructor() {
    super({
      name: 'TGDWallpaperManager',
      platforms: ['web'],
    });
  }

  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }

  async applyWallpaper(options: { value: string }): Promise<{ value: string }> {
    console.log('Cannot work in web', options);
    return options;
  }
  
}

const TGDWallpaperManager = new TGDWallpaperManagerWeb();

export { TGDWallpaperManager };

import { registerWebPlugin } from '@capacitor/core';
registerWebPlugin(TGDWallpaperManager);
