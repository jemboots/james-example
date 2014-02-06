//
//  CommonClass.m
//  HideUINavigationPushSegueExample
//
//  Created by James on 6/2/14.
//  Copyright (c) 2014 James. All rights reserved.
//

#import "CommonClass.h"

@implementation CommonClass
@synthesize navigationController;

static CommonClass *instance = nil;

+ (CommonClass *) instance {
    if (instance == nil) {
        instance = [[CommonClass alloc] init];
    }
    
    return instance;
}

- (FirstViewController *) getFirstView
{
    if (firstView == nil) {
        firstView = [[FirstViewController alloc] init];
    }
    
    return firstView;
}

- (SecondViewController *) getSecondView
{
    if (secondView == nil) {
        secondView = [[SecondViewController alloc] init];
    }
    
    return secondView;
}

- (ThirdViewController *) getThirdView
{
    if (thirdView == nil) {
        thirdView = [[ThirdViewController alloc] init];
    }
    
    return thirdView;
}

- (UINavigationController *) getNavigator:(UIViewController *)viewController
{
    if (navigationController == nil) {
        navigationController = [[UINavigationController alloc] initWithRootViewController:viewController];
    }
    
    return navigationController;
}
@end
