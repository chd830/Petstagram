import Vue from 'vue'
import Router from 'vue-router'
// 연결할 컴포넌트 import
// import Topic from "./../components/Topic.vue"
// import HelloWorld from "@/components/HelloWorld.vue"
// import IdolList from "@/components/IdolList.vue"
// import WriteUwasa from "@/components/WriteUwasa.vue"
import VueRouter from 'vue-router'

// 필수
Vue.use(Router)

const routes = [
    {
        path: "/",
        name: "HelloWorld",
        component: () => import('./../components/HelloWorld.vue')
    },
    // {
    //     path: "/", // 경로
    //     name: "Topic", // 해당 경로의 이름 
    //     component: Topic // 이동할 컴포넌트
    // },
    {
        path: "/posts/",
        name: "PostsView",
        component: () => import('./../components/Posts/PostsView.vue')
    },
    {
        path: "/posts/insert/",
        name: "PostsInsert",
        component: () => import('./../components/Posts/PostsInsert.vue')
    }
]
const router = new VueRouter({
    mode: 'history',
    routes
})

export default router