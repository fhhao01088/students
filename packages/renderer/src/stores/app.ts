import { defineStore } from 'pinia';
import { runtimeEnvironment } from '@renderer/config/environment';

interface AppState {
  isRunning: boolean;
  lastResponse: string | null;
  environment: string;
}

export const useAppStore = defineStore('app', {
  state: (): AppState => ({
    isRunning: false,
    lastResponse: null,
    environment: runtimeEnvironment.appEnv
  }),
  actions: {
    async runDiagnostics() {
      if (!window.bridge?.ping) {
        this.lastResponse = 'bridge-unavailable';
        return;
      }

      this.isRunning = true;
      try {
        const response = await window.bridge.ping('diagnostic-probe');
        this.lastResponse = response;
      } catch (error) {
        console.error('Failed to communicate with native bridge', error);
        this.lastResponse = 'bridge-error';
      } finally {
        this.isRunning = false;
      }
    }
  }
});
