//
//  ViewController.m
//  SWFAnimationInIOS
//
//  Created by James on 5/1/13.
//  Copyright (c) 2013 __MyCompanyName__. All rights reserved.
//

#import "ViewController.h"
#import "PNGAnimationView.h"

@implementation ViewController

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
    
    animationView = [[PNGAnimationView alloc] initWithFrame:CGRectMake(0, 0, 245, 317) fileNamePattern:@"PlatypusIdle%@.png" startFrame:1 totalFrame:20 interval:0.04];
    [animationView setIsLoop:YES];
    [self.view addSubview:animationView];
    [animationView playAnimation];
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
}

- (void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:animated];
}

- (void)viewWillDisappear:(BOOL)animated
{
	[super viewWillDisappear:animated];
}

- (void)viewDidDisappear:(BOOL)animated
{
	[super viewDidDisappear:animated];
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return YES;
}

- (IBAction)startAnimation:(id)sender
{
    [animationView playAnimation];
}

- (IBAction)stopAnimation:(id)sender
{
    [animationView stopAnimation];
}
@end
