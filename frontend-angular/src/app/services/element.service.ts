import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Element } from '../models/element';

@Injectable({
  providedIn: 'root',
})
export class ElementService {

  private apiUrl = '/api/leagues';

  constructor(private http: HttpClient) { }

  // druzyny by liga
  getElementsByCategory(categoryId: string): Observable<Element[]> {
    return this.http.get<Element[]>(`${this.apiUrl}/${categoryId}/teams`);
  }

  // jedna druzyna
  getElement(leagueId: string, elementId: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/${leagueId}/teams/${elementId}`);
  }

  // nowa druzyna
  createElement(leagueId: string, element: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/${leagueId}/teams`, element);
  }

  // edycja druzyny
  updateElement(leagueId: string, elementId: string, element: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/${leagueId}/teams/${elementId}`, element);
  }

  // usun
  deleteElement(categoryId: string, elementId: string): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${categoryId}/teams/${elementId}`);
  }

}
