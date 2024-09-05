export type Post = {
    id:number
    title:string
    topic:string
    content:string
    date:string
    authorName:string
    comments: UserComment[]
}

export type UserComment = {
    username: string
    comment: string
    date: string
}