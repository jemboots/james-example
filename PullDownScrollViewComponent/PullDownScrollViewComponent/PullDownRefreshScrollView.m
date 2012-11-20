//
//  PullDownRefreshScrollView.m
//  bbbbb
//
//  Created by James on 11/19/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "PullDownRefreshScrollView.h"
#import <QuartzCore/CALayer.h>

@implementation PullDownRefreshScrollView
@synthesize latestUpdateDate;

- (void) initPushLoadingView
{
    [self setDelegate:self];
    
    refreshView = [[UIView alloc] initWithFrame:CGRectMake(0, 0 - REFHEIGHT, 320, REFHEIGHT)];
    refreshLabel = [[UILabel alloc] initWithFrame:CGRectMake(0, 0, 320, REFHEIGHT)];
    refreshLabel.textAlignment = UITextAlignmentCenter;
    refreshLabel.text = PULL_DOWN_REFRESH;
    
    refreshArrow = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"arrow.png"]];
    refreshArrow.frame = CGRectMake(17, 9, 27, 44);
    refreshLoadingIcon = [[UIActivityIndicatorView alloc] init];
    refreshLoadingIcon.frame = CGRectMake(20, 20, 20, 20);
    refreshLoadingIcon.activityIndicatorViewStyle = UIActivityIndicatorViewStyleGray;
    refreshLoadingIcon.hidesWhenStopped = YES;
    
    refreshDateLabel = [[UILabel alloc] initWithFrame:CGRectMake(0, 40, 320, 21)];
    refreshDateLabel.textColor = [UIColor darkGrayColor];
    refreshDateLabel.font = [UIFont systemFontOfSize:10];
    refreshDateLabel.textAlignment = UITextAlignmentCenter;
    if (latestUpdateDate != nil) {
        [refreshDateLabel setText:[NSString stringWithFormat:@"%@%@", REFRESH_DATE_PREFIX, latestUpdateDate]];
    }
    
    [refreshView addSubview:refreshLabel];
    [refreshView addSubview:refreshLoadingIcon];
    [refreshView addSubview:refreshDateLabel];
    [refreshView addSubview:refreshArrow];
    [self addSubview:refreshView];
    
    isLoading = false;
}

-(void)scrollViewWillBeginDragging:(UIScrollView *)scrollView
{
    NSLog(@"scroll View Begin Dragging");
    isDraging = true;
}

-(void)scrollViewDidScroll:(UIScrollView *)myscrollView
{
    NSLog(@"scroll view scroll: %f", myscrollView.contentOffset.y);
    
    if(!isLoading)
    {
        if (isDraging && myscrollView.contentOffset.y < 0 - REFHEIGHT)
        {
            [UIView animateWithDuration:0.25 animations:^{
                refreshLabel.text = RELEASE_REFRESH;
                CALayer *layer = refreshArrow.layer;
                layer.transform = CATransform3DMakeRotation(M_PI, 0, 0, 1);
            }];
        }
        else
        {
            [UIView animateWithDuration:0.25 animations:^{
                refreshLabel.text = PULL_DOWN_REFRESH;
                [refreshArrow layer].transform = CATransform3DMakeRotation(M_PI * 2, 0, 0, 1);
            }];
        }
    }
}

-(void)scrollViewDidEndDragging:(UIScrollView *)myscrollView willDecelerate:(BOOL)decelerate
{
    isDraging = false;
    if (myscrollView.contentOffset.y < 0 - REFHEIGHT)
    {
        
        [UIView animateWithDuration:0.5 animations:^{
            self.contentInset = UIEdgeInsetsMake(REFHEIGHT, 0, 0, 0);
        }];
        
        refreshLabel.text = LOADING;
        isLoading = true;
        
        [refreshLoadingIcon startAnimating];
        [refreshArrow setHidden:YES];
        
        [self performSelector:@selector(stopLoading) withObject:nil afterDelay:2.0];
    }
}

- (void)stopLoading
{
    isLoading = false;
    [refreshLoadingIcon stopAnimating];
    [refreshArrow setHidden:NO];
    
    // Hide the header
    [UIView animateWithDuration:0.3 animations:^{
        self.contentInset = UIEdgeInsetsZero;
    }
                     completion:^(BOOL finished) {
                         [self performSelector:@selector(stopLoadingComplete)];
                     }];
}

- (void)stopLoadingComplete
{
    // Reset the header
    refreshLabel.text = PULL_DOWN_REFRESH;
    
    NSDate *now = [[NSDate alloc] init];
    NSDateFormatter *formatter = [[NSDateFormatter alloc] init];
    formatter.dateFormat = @"dd/MM/yy HH:mm"; 
    latestUpdateDate = [formatter stringFromDate:now];
    [refreshDateLabel setText:[NSString stringWithFormat:@"%@%@", REFRESH_DATE_PREFIX, latestUpdateDate]];
}
@end
