import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import Forecast from '@/components/Forecast'
import History from '@/components/History'
import Information from '@/components/Information'
import InformationA from '@/components/InformationA'
import Home from '@/components/Home'
import Admin from '@/components/Admin'
import Option from '@/components/Option'
import OptionA from '@/components/OptionA'
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/Home',
      name: 'Home',
      component: Home
    },
    {
      path: '/Forecast',
      name: 'Forecast',
      component: Forecast
    },
    {
      path: '/',
      name: 'Login',
      component: Login
    },
    {
      path: '/History',
      name: 'History',
      component: History
    },
    {
      path: '/Information',
      name: 'Information',
      component: Information
    },
    {
      path: '/Admin',
      name: 'Admin',
      component: Admin
    },
    {
      path: '/Option',
      name: 'Option',
      component: Option,
      children: [
        {
          path: '/',
          component: Home
        },
        {
          path: 'Forecast',
          component: Forecast
        },
        {
          path: 'History',
          component: History
        },
        {
          path: 'Information',
          component: Information
        }
      ]
    },
    {
      path: '/OptionA',
      name: 'OptionA',
      component: OptionA,
      children: [
        {
          path: '/',
          component: Home
        },
        {
          path: 'Forecast',
          component: Forecast
        },
        {
          path: 'History',
          component: History
        },
        {
          path: 'InformationA',
          component: InformationA
        },
        {
          path: 'Admin',
          component: Admin
        }
      ]
    }
  ]
})
