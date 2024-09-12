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
  private apiGetTopicsUser = 'http://localhost:8080/api/topics/subscriptions'
  private apiSubscribe = 'http://localhost:8080/api/topics/subscribe'
  private apiUnsubscribe = 'http://localhost:8080/api/topics/unsubscribe'

  constructor(
    private http: HttpClient
  ) { }

  // Fetch all topics
  getTopics(): Observable<Topic[]>{
    return this.http.get<Topic[]>(this.apiGetTopics)
    .pipe(
      catchError(error => {
        return throwError(() => new Error(error));
      })
    )
  }

  // Subscribe to a specific topic
  subscribeTopic(topicId: number): Observable<Subscription> {
    return this.http.post<Subscription>(`${this.apiSubscribe}/${topicId}`, {})
  }
  
  // Unsubscribe from a specific topic
  unsubscribeTopic(topicId: number): Observable<Subscription> {
    return this.http.post<Subscription>(`${this.apiUnsubscribe}/${topicId}`, {})
  }

  // Fetch all topics the user is subscribed to
  getTopicsByUser(): Observable<Topic[]>{
    return this.http.get<Topic[]>(this.apiGetTopicsUser)
  }
}
