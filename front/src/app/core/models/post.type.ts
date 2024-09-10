export type Post = {
    id:number
    title:string
    topicName:string
    content:string
    date:string
    author:string
    comments: UserComment[]
}

export type UserComment = {
    id:number
    author: string
    description: string
    date: string
    postTitle: string
}

export type CreateComment = {
    description: string
    date: string
}