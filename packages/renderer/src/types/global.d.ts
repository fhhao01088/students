export {};

declare global {
  interface Window {
    bridge?: {
      ping: (message: string) => Promise<string>;
    };
  }
}
