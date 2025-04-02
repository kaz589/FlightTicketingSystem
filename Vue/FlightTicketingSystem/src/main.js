//建立APP
import { createApp } from 'vue'
import App from './App.vue'
const app = createApp(App)
//router插件
import router from './router'
app.use(router)

//Pinia插件
import { createPinia } from 'pinia'
import persisted from 'pinia-plugin-persistedstate'

const pinia = createPinia()
pinia.use(persisted)
app.use(pinia)

//vuetify
import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'
import '@mdi/font/css/materialdesignicons.css'

const vuetify = createVuetify({
    components,
    directives,
  })

app.use(vuetify) // 使用 Vuetify
app.mount('#app')
