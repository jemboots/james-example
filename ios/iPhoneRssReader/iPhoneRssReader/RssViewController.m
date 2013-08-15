//
//  RssViewController.m
//  iPhoneRssReader
//
//  Created by James on 8/15/13.
//  Copyright (c) 2013 __MyCompanyName__. All rights reserved.
//

#import "RssViewController.h"
#import "RssData.h"

@implementation RssViewController
@synthesize rssWebView;
@synthesize rssData;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)didReceiveMemoryWarning
{
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

/*
// Implement loadView to create a view hierarchy programmatically, without using a nib.
- (void)loadView
{
}
*/


// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad
{
    [super viewDidLoad];
    NSString *htmlContent = rssData.content;
    htmlContent = [NSString stringWithFormat:@"%@<h1>%@</h1><div>%@</div>%@%@", HTML_HEADER, rssData.title, rssData.publishDate, htmlContent, HTML_FOOTER];
    [rssWebView loadHTMLString:htmlContent baseURL:nil];
}
 

- (void)viewDidUnload
{
    [self setRssWebView:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
	return YES;
}

@end
