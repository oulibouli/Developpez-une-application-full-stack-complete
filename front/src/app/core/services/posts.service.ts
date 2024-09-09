import { Injectable } from '@angular/core';
import { catchError, Observable, tap, throwError } from 'rxjs';
import { Post } from '../models/post.type';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class PostsService {
  private apiPosts = 'http://localhost:8080/api/posts'

  constructor(
    private http: HttpClient
  ) {}

  getAllPosts():Observable<Post[]> {
    return this.http.get<Post[]>(this.apiPosts)
      .pipe(
        tap((response) => {
          return response
        }),
        catchError((error) => {
          return throwError(() => new Error(error))
        })
      )
  }
}
