import { Component } from '@angular/core';
import { interval, Subscription } from 'rxjs';
import { ActiveDecorator, PatternList } from './patternList';
import { Strip } from './strip';
import { StripServiceService } from './strip-service.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title: string = 'rgb-client';
  strip: Strip | undefined;
  color: string = '#FF0000';
  patterns: PatternList = {};
  decorators: PatternList = {};
  selectedPattern: string = '';
  selectedDecorator: string = '';
  activeDecorators: ActiveDecorator[] = [];

  subscription?: Subscription|undefined;


  constructor(private stripService: StripServiceService) {
  }

  private startNewSubscription() {
    this.subscription = interval(33).subscribe(() => this.resetStrip());
  }

  private fetchActiveDecorators() {
    this.stripService.getActiveDecorators().subscribe(res => {
      this.activeDecorators = res;
    })
  }

  ngOnInit() {
    this.resetStrip();
    this.stripService.getPatterns().subscribe(res => {
      this.patterns = res;
      this.selectedPattern = '';
      this.selectedPattern=localStorage.getItem('selectedPattern') || '';
    });
    this.stripService.getDecorators().subscribe(res => {
      this.decorators = res;
      this.selectedDecorator = '';
      this.selectedDecorator=localStorage.getItem('selectedDecorator') || '';
      this.fetchActiveDecorators();
    });
    
    this.selectedPattern=localStorage.getItem('selectedPattern') || '';
    this.selectedDecorator=localStorage.getItem('selectedDecorator') || '';

  }

  resetStrip() {
    this.stripService.getStripInfo().subscribe(result => {
      if(!this.strip?.continuous && result.continuous) {
        this.startNewSubscription();
      }
      else if(this.strip?.continuous && !result.continuous) {
        this.subscription?.unsubscribe();
      }
      this.strip = result;
    });
  }

  submitPattern() {
    if(this.selectedPattern == null) return;
    this.stripService.submitPattern(this.patterns, this.selectedPattern).subscribe(result => this.resetStrip());
  }

  submitDecorator() {
    if(this.selectedDecorator == null) return;
    this.stripService.submitDecorator(this.decorators, this.selectedDecorator).subscribe(result => {
      this.fetchActiveDecorators();
      this.resetStrip();
    });
  }

  submitColor() {
    this.stripService.setSingleColor(parseInt(this.color.substring(1), 16)).subscribe(result => this.resetStrip());
  }
  
  parameterChanged() {
    localStorage.setItem('decoratorCache', JSON.stringify(this.decorators));
    localStorage.setItem('patternCache', JSON.stringify(this.patterns));
    if(this.selectedPattern != null)  localStorage.setItem('selectedPattern', this.selectedPattern);
    if(this.selectedDecorator != null)localStorage.setItem('selectedDecorator', this.selectedDecorator);
  }

  removeDecorator(index: number) {
    this.stripService.removeDecorator(index).subscribe(() => {
      this.fetchActiveDecorators();
      this.resetStrip();
    });
  }
}
