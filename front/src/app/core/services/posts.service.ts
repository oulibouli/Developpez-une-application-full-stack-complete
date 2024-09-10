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

  constructor(
    private http: HttpClient
  ) {}

  getAllPosts():Observable<Post[]> {
    return this.http.get<Post[]>(this.apiPosts)
      .pipe(
        catchError((error) => {
          return throwError(() => new Error(error))
        })
      )
  }

  getPostById(id:number): Observable<Post> {
    return this.http.get<Post>(`${this.apiPosts}/${id}`)
      .pipe(
        catchError((error) => {
          return throwError(() => new Error(error))
        })
      )
  }

  addComment(comment: CreateComment, postId: number): Observable<UserComment> {
    return this.http.post<UserComment>(`${this.apiComment}/${postId}`, comment)
    .pipe(
      catchError(error => {
        return throwError(() => new Error(error))
      }) 
    )
  }
}
