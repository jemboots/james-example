//
//  PullDownRefreshTableViewController.h
//  PullDownScrollViewComponent
//
//  Created by James on 11/21/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

#define REFHEIGHT   60

#define PULL_DOWN_REFRESH   @"Pull down to refresh..."
#define RELEASE_REFRESH     @"Release to refresh..."
#define LOADING             @"Loading..."
#define PULL_UP_GET_MORE    @"Pull up to get more news..."
#define RELEASE_TO_LOADING  @"Release to loading..."
#define REFRESH_DATE_PREFIX @"Last Updated: "

@interface PullDownRefreshTableViewController : UITableViewController <UIScrollViewDelegate>
{
    NSString *textPull;
    NSString *textRelease;
    NSString *textLoading;
    
    UILabel *refreshLabel;
    UIImageView *refreshArrow;
    UIActivityIndicatorView *refreshLoadingIcon;
    UILabel *refreshDateLabel;
    UIView  *refreshView;
    
    Boolean isLoading;
    Boolean isDraging;
}

@property (assign, nonatomic) NSString *latestUpdateDate;
@property (retain, nonatomic) NSMutableArray *exampleData;

@end
