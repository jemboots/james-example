//
//  HorizontalSlideSegue.m
//  adsupporttest
//
//  Created by James on 4/19/13.
//  Copyright (c) 2013 __MyCompanyName__. All rights reserved.
//

#import "HorizontalSlideSegue.h"

@implementation HorizontalSlideSegue
@synthesize isDismiss;
@synthesize isLandscapeOrientation;

- (void) perform
{
    UIViewController *desViewController = (UIViewController *)self.destinationViewController;
    
    UIView *srcView = [(UIViewController *)self.sourceViewController view];
    UIView *desView = [desViewController view];
    
    desView.transform = srcView.transform;
    desView.bounds = srcView.bounds;
    
    if(isLandscapeOrientation)
    {
        if(isDismiss)
        {
            desView.center = CGPointMake(srcView.center.x, srcView.center.y  - srcView.frame.size.height);
        }
        else
        {
            desView.center = CGPointMake(srcView.center.x, srcView.center.y  + srcView.frame.size.height);
        }
    }
    else
    {
        if(isDismiss)
        {
            desView.center = CGPointMake(srcView.center.x - srcView.frame.size.width, srcView.center.y);
        }
        else
        {
            desView.center = CGPointMake(srcView.center.x + srcView.frame.size.width, srcView.center.y);
        }
    }
    
    
    UIWindow *mainWindow = [[UIApplication sharedApplication].windows objectAtIndex:0];
    [mainWindow addSubview:desView];
    
    // slide newView over oldView, then remove oldView
    [UIView animateWithDuration:0.3
                     animations:^{
                         desView.center = CGPointMake(srcView.center.x, srcView.center.y);
                         
                         if(isLandscapeOrientation)
                         {
                             if(isDismiss)
                             {
                                 srcView.center = CGPointMake(srcView.center.x, srcView.center.y + srcView.frame.size.height);
                             }
                             else
                             {
                                 srcView.center = CGPointMake(srcView.center.x, srcView.center.y - srcView.frame.size.height);
                             }
                         }
                         else
                         {
                             if(isDismiss)
                             {
                                 srcView.center = CGPointMake(srcView.center.x + srcView.frame.size.width, srcView.center.y);
                             }
                             else
                             {
                                 srcView.center = CGPointMake(srcView.center.x - srcView.frame.size.width, srcView.center.y);
                             }
                         }
                     }
                     completion:^(BOOL finished){
                         //[desView removeFromSuperview];
                         [self.sourceViewController presentModalViewController:desViewController animated:NO];
                     }];
}
@end
