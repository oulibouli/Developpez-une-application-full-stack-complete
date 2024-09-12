import { Injectable } from '@angular/core';
import { catchError, Observable, tap, throwError } from 'rxjs';
import { CreateComment, Post, UserComment } from '../models/post.type';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class PostsService {
  private apiPosts = 'http://localhost:8080/api/posts'
  private apiComment = 'http://localhost:8080/api/comment'
  private apiCreatePost = 'http://localhost:8080/api/posts'

  constructor(
    private http: HttpClient
  ) {}

  // Fetch all posts from the server
  getAllPosts():Observable<Post[]> {
    return this.http.get<Post[]>(this.apiPosts)
      .pipe(
        catchError((error) => {
          return throwError(() => new Error(error))
        })
      )
  }

  // Fetch a specific post by its ID
  getPostById(id:number): Observable<Post> {
    return this.http.get<Post>(`${this.apiPosts}/${id}`)
      .pipe(
        catchError((error) => {
          return throwError(() => new Error(error))
        })
      )
  }

  // Add a new comment to a post
  addComment(comment: Partial<CreateComment>, postId: number): Observable<UserComment> {
    return this.http.post<UserComment>(`${this.apiComment}/${postId}`, comment)
    .pipe(
      catchError(error => {
        return throwError(() => new Error(error))
      })
    )
  }

  // Create a new post for a specific topic
  createPost(post: Partial<Post>, topicId: number): Observable<Post> {
    return this.http.post<Post>(`${this.apiCreatePost}/${topicId}`, post)
    .pipe(
      catchError(error => {
        return throwError(() => new Error(error))
      })
    )
  }
}
