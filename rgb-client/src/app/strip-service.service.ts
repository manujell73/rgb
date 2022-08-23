import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Strip } from './strip';
import { Observable } from 'rxjs';
import { Pattern } from './pattern';

@Injectable({
  providedIn: 'root'
})
export class StripServiceService {
  private stripUrl: string;
  private patternsUrl: string;
  private setColorUrl: string;

  constructor(private http: HttpClient) {
    this.stripUrl = 'http://localhost:8080/getStripInfo';
    this.patternsUrl = 'http://localhost:8080/getPatterns';
    this.setColorUrl = 'http://localhost:8080/setPattern';
  }

  public getPatterns(): Observable<Pattern[]> {
    return this.http.get<Pattern[]>(this.patternsUrl);
  }

  public getStripInfo(): Observable<Strip> {
    return this.http.get<Strip>(this.stripUrl);
  }

  public setSingleColor(color:number): Observable<Strip> {
    let params: HttpParams = new HttpParams()
      .set('patternCode', 0)
      .set('parameters', color);
    return this.http.post<Strip>(this.setColorUrl, {}, {params: params});
  }

  public submitPattern(pattern:Pattern): Observable<Strip> {
    let patternParams = [];
    for(let i =0; i < pattern.parameters.length; i++){
      let value;
      if(pattern.parameters[i].type === 'COLOR')
        value = parseInt(pattern.parameters[i].value.substring(1), 16);
      else
        value = pattern.parameters[i].value;
      patternParams[i] = value;
    }

    let params: HttpParams = new HttpParams()
      .set('patternCode', pattern.id)
      .set('parameters', patternParams.join(','));
    return this.http.post<Strip>(this.setColorUrl, {}, {params: params});
  }
}
