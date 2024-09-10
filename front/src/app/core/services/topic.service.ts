import { Injectable } from '@angular/core';
import { catchError, Observable, tap, throwError } from 'rxjs';
import { Topic } from '../models/topic.type';
import { HttpClient } from '@angular/common/http';
import { Subscription } from '../models/subscription.type';

@Injectable({
  providedIn: 'root'
})
export class TopicService {
  private apiGetTopics = 'http://localhost:8080/api/topics'
  private apiSubscribe = 'http://localhost:8080/api/topics/subscribe'

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

  subscribeTopic(topicId: number): Observable<Subscription> {
    return this.http.post<Subscription>(`${this.apiSubscribe}/${topicId}`, {})
    .pipe(
      catchError(error => {
        return throwError(() => new Error(error.message || 'Unknown error occurred'));
      })
    )
  }
}
