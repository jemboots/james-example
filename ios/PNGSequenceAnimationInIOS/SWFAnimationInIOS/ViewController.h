//
//  ViewController.h
//  SWFAnimationInIOS
//
//  Created by James on 5/1/13.
//  Copyright (c) 2013 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "PNGAnimationView.h"

@interface ViewController : UIViewController
{
    PNGAnimationView *animationView;
}

- (IBAction)startAnimation:(id)sender;
- (IBAction)stopAnimation:(id)sender;

@end
