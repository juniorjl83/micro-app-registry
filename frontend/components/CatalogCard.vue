<script setup lang="ts">
import { defineProps } from 'vue'

const props = defineProps({
  app: {
    type: Object as () => {
      id: string
      name: string
      shortDescription: string
      priceCents: number
      currency: string
      trialEnabled: boolean
    },
    required: true,
  },
})

const price = (props.app.priceCents / 100).toFixed(2)
</script>

<template>
  <div class="border rounded p-4 shadow hover:shadow-lg transition">
    <h2 class="text-xl font-bold">{{ props.app.name }}</h2>
    <p class="text-gray-600">{{ props.app.shortDescription }}</p>
    <p class="mt-2">Price: {{ price }} {{ props.app.currency }}</p>
    <div class="mt-4">
      <button v-if="props.app.trialEnabled" class="bg-green-600 text-white px-3 py-1 rounded mr-2">Start Trial</button>
      <button class="bg-blue-600 text-white px-3 py-1 rounded">Buy Now</button>
    </div>
  </div>
</template>
