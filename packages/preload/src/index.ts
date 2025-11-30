import { contextBridge, ipcRenderer } from 'electron';

type BridgeApi = {
  ping: (message: string) => Promise<string>;
};

const api: BridgeApi = {
  ping: async (message: string) => ipcRenderer.invoke('bridge:ping', message)
};

contextBridge.exposeInMainWorld('bridge', Object.freeze(api));
