import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Strip } from './strip';
import { Observable } from 'rxjs';
import { Parameter, PatternList } from './patternList';

@Injectable({
  providedIn: 'root'
})
export class StripServiceService {
  private stripUrl: string;
  private patternsUrl: string;
  private setColorUrl: string;
  private decoratorUrl: string;
  private setDecoratorUrl: string;

  constructor(private http: HttpClient) {
    this.stripUrl = 'http://localhost:8080/getStripInfo';
    this.patternsUrl = 'http://localhost:8080/getPatterns';
    this.setColorUrl = 'http://localhost:8080/setPattern';
    this.decoratorUrl = 'http://localhost:8080/getDecorators';
    this.setDecoratorUrl = 'http://localhost:8080/setDecorator';
  }

  public getPatterns(): Observable<PatternList> {
    return this.http.get<PatternList>(this.patternsUrl);
  }

  public getDecorators(): Observable<PatternList> {
    return this.http.get<PatternList>(this.decoratorUrl);
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

  public submitPattern(patternList:PatternList, patternName:string): Observable<Strip> {
    return this.submit(patternList, patternName, this.setColorUrl);
  }

  public submitDecorator(patternList:PatternList, patternName:string): Observable<Strip> {
    return this.submit(patternList, patternName, this.setDecoratorUrl);
  }

  private submit(patternList:PatternList, patternName:string, requestUrl:string): Observable<Strip> {
    let patternParams:(string|number)[] = [];
    let parameters:Parameter[] = patternList[patternName];
    for(let i =0; i < parameters.length; i++){
      let value;
      if(parameters[i].type === 'COLOR')
        value = parseInt(parameters[i].value.substring(1), 16);
      else
        value = parameters[i].value;
      patternParams[i] = value;
    }

    let params: HttpParams = new HttpParams()
      .set('name', patternName)
      .set('parameters', patternParams.join(','));
    return this.http.post<Strip>(requestUrl, {}, {params: params});
  }
}
