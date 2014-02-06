//
//  CommonClass.h
//  HideUINavigationPushSegueExample
//
//  Created by James on 6/2/14.
//  Copyright (c) 2014 James. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SecondViewController.h"
#import "ThirdViewController.h"
#import "FirstViewController.h"

@interface CommonClass : NSObject
{
    FirstViewController *firstView;
    SecondViewController *secondView;
    ThirdViewController *thirdView;
}

@property (retain, nonatomic) UINavigationController *navigationController;

+(CommonClass *) instance;
-(FirstViewController *) getFirstView;
-(SecondViewController *) getSecondView;
-(ThirdViewController *) getThirdView;
-(UINavigationController *) getNavigator: (UIViewController *) viewController;
@end
