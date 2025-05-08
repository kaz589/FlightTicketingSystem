<template>
  <v-row>
    <v-col cols="6">
      <v-expansion-panels v-model="panelLeft" multiple>
        <v-expansion-panel
          v-for="item in leftItems"
          :key="item.value"
          :title="item.title"
          :text="item.text"
          :value="item.value"
        ></v-expansion-panel>
      </v-expansion-panels>
    </v-col>
    <v-col cols="6">
      <v-expansion-panels v-model="panelRight" multiple>
        <v-expansion-panel
          v-for="item in rightItems"
          :key="item.value"
          :title="item.title"
          :text="item.text"
          :value="item.value"
        ></v-expansion-panel>
      </v-expansion-panels>
    </v-col>
  </v-row>
</template>

<script setup>
import { ref, computed } from 'vue'

const panelLeft = ref([])
const panelRight = ref([])

const items = [
  {
    title: 'Easytrip 是如何運作的？',
    value: 'foo',
    text:
      'Easytrip 是一個航班、租車服務和飯店的搜尋引擎。我們為你蒐羅網路上所有熱門的航空公司與旅行業者，方便你一站式比較航班價格及其他旅行費用。找到最划算的航班、租車服務或飯店後，你可以直接向供應商預訂。',
  },
  {
    title: '如何能使用 Easytrip 找到最便宜的航班？',
    value: 'bar',
    text:
      `在這裡可以輕鬆找到航班，每個月都有超過 1 億名精明的旅客前來尋找便宜的機票、飯店和租車服務。以下是幾個簡單的祕訣，可以幫助你充分利用航班搜尋功能。
省時又省荷包。無論是要查找最快捷的航班，或是最明智的住宿選項，你都可以根據旅客的真實評價，挑選喜歡的航班供應商或飯店，並立即預訂。

搜尋世界各地，造訪各個角落。很想去旅行，但去哪裡都沒關係？或者想發掘新的好去處？不妨「搜尋世界各地」，查看任何一天各地最優惠的廉價機票。

尋找最便宜的出遊日期。如果你已決定好目的地，且想尋找最便宜的機票，請在搜尋時選擇「最便宜的月份」，就能查看最便宜的出發日期的航班，輕鬆比價。`,
  },
  {
    title: '目前適合預訂前往哪些地區的航班？',
    value: 'baz',
    text:
      'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.',
  },
  {
    title: '我是向 Easytrip 預訂機票嗎？',
    value: 'baz',
    text:
      'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.',
  },
  {
    title: '預訂航班之後要注意哪些事項？',
    value: 'baz',
    text:
      'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.',
  },
  {
    title: 'Easytrip 也提供飯店服務嗎？',
    value: 'baz',
    text:
      'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.',
  },
  {
    title: '另外還提供租車嗎？',
    value: 'baz',
    text:
      'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.',
  },
  {
    title: '價格通知是什麼？',
    value: 'baz',
    text:
      'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.',
  },
  {
    title: '我可以預訂彈性機票嗎？',
    value: 'baz',
    text:
      'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.',
  },
  {
    title: '我可以預訂排放較少二氧化碳的航班嗎？',
    value: 'baz',
    text:
      'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.',
  },
]

const half = Math.ceil(items.length / 2)
const leftItems = computed(() => items.slice(0, half))
const rightItems = computed(() => items.slice(half))
</script>
