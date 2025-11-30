export type RuntimeEnvironment = {
  mode: string;
  appEnv: string;
  isProduction: boolean;
};

export const runtimeEnvironment: RuntimeEnvironment = {
  mode: import.meta.env.MODE,
  appEnv: import.meta.env.VITE_APP_ENV ?? import.meta.env.MODE ?? 'development',
  isProduction: import.meta.env.PROD
};
