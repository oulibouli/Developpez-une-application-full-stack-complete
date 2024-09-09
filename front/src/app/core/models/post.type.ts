export type Post = {
    id:number
    title:string
    topic:string
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
}