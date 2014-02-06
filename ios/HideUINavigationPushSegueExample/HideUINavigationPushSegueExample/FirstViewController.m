//
//  FirstViewController.m
//  HideUINavigationPushSegueExample
//
//  Created by James on 6/2/14.
//  Copyright (c) 2014 James. All rights reserved.
//

#import "FirstViewController.h"
#import "CommonClass.h"

@interface FirstViewController ()

@end

@implementation FirstViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)gotoNextView:(id)sender
{
    UIViewController *nextView = [[CommonClass instance] getSecondView];
    UINavigationController *nav = [[CommonClass instance] navigationController];
    
    [nav pushViewController:nextView animated:YES];
}
@end
