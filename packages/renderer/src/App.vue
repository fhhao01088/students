<template>
  <div class="app-shell">
    <header>
      <h1>{{ t('app.title') }}</h1>
      <p class="subtitle">{{ t('app.subtitle') }}</p>
    </header>

    <main>
      <p>{{ t('app.description') }}</p>
      <p class="environment">{{ t('app.environment', { env: appStore.environment }) }}</p>

      <div class="actions">
        <button :disabled="isProcessing" @click="triggerDiagnostics">
          {{ isProcessing ? t('actions.processing') : t('actions.runDiagnostics') }}
        </button>
      </div>

      <p v-if="lastResponse" class="status">
        {{ t('app.lastResponse', { message: responseLabel }) }}
      </p>
      <p v-else class="footer-note">{{ t('status.idle') }}</p>
    </main>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue';
import { useI18n } from 'vue-i18n';
import { useAppStore } from '@renderer/stores/app';

const { t } = useI18n();
const appStore = useAppStore();

const isProcessing = computed(() => appStore.isRunning);
const lastResponse = computed(() => appStore.lastResponse);

const responseLabel = computed(() => {
  if (!lastResponse.value) {
    return '';
  }

  if (lastResponse.value === 'bridge-unavailable') {
    return t('status.bridgeUnavailable');
  }

  if (lastResponse.value === 'bridge-error') {
    return t('status.bridgeError');
  }

  if (lastResponse.value.startsWith('ack:')) {
    return t('status.successful');
  }

  return lastResponse.value;
});

const triggerDiagnostics = async () => {
  await appStore.runDiagnostics();
};
</script>
