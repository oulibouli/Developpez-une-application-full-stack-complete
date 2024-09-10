import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { Topic } from '../models/topic.type';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class TopicService {
  private apiGetTopics = 'http://localhost:8080/api/topics'

  constructor(
    private http: HttpClient
  ) { }

  getTopics(): Observable<Topic[]>{
    return this.http.get<Topic[]>(this.apiGetTopics)
    .pipe(
      catchError(error => {
        return throwError(() => new Error(error));
      })
    )
  }
}
