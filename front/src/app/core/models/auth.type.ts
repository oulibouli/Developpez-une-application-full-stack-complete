export type LoginRequest = {
    identifier: string
    password: string
}
export type RegisterRequest = {
    email: string
    username: string
    password: string
}
export type UpdateRequest = {
    email: string
    username: string
    password: string
}