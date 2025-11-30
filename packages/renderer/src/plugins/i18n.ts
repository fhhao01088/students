import { createI18n } from 'vue-i18n';
import zhCN from '@renderer/locales/zh-CN.json';
import enUS from '@renderer/locales/en-US.json';

export const i18n = createI18n({
  legacy: false,
  locale: 'zh-CN',
  fallbackLocale: 'en-US',
  messages: {
    'zh-CN': zhCN,
    'en-US': enUS
  }
});
