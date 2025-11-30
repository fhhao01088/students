import { resolve } from 'node:path';
import { defineConfig } from 'electron-vite';
import vue from '@vitejs/plugin-vue';
import vueI18n from '@intlify/vite-plugin-vue-i18n';

export default defineConfig({
  main: {
    build: {
      outDir: 'dist-electron/main',
      sourcemap: true,
      rollupOptions: {
        input: resolve(__dirname, 'packages/main/src/index.ts')
      }
    }
  },
  preload: {
    build: {
      outDir: 'dist-electron/preload',
      sourcemap: true,
      rollupOptions: {
        input: resolve(__dirname, 'packages/preload/src/index.ts')
      }
    }
  },
  renderer: {
    root: resolve(__dirname, 'packages/renderer'),
    envDir: resolve(__dirname, '.'),
    resolve: {
      alias: {
        '@renderer': resolve(__dirname, 'packages/renderer/src'),
        '@main': resolve(__dirname, 'packages/main/src'),
        '@preload': resolve(__dirname, 'packages/preload/src')
      }
    },
    plugins: [
      vue(),
      vueI18n({
        include: resolve(__dirname, 'packages/renderer/src/locales/**'),
        defaultSFCLang: 'json'
      })
    ],
    build: {
      outDir: resolve(__dirname, 'dist'),
      emptyOutDir: true,
      sourcemap: true
    }
  }
});
