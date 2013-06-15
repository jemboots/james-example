//
//  RssHttpController.m
//  iPhoneRssReader
//
//  Created by James on 12/15/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "RssHttpController.h"

@implementation RssHttpController

-(NSURLConnection *)getRSSContent:(NSString *)pageID rssurl:(NSString *)url delegate:(id<NSURLConnectionDataDelegate>)delegate
{
    NSURL *urlRef = [NSURL URLWithString:[url stringByAppendingString:pageID]];
    
    NSURLRequest *rssRequest = [NSURLRequest requestWithURL:urlRef];
    NSURLConnection *connect = [[NSURLConnection alloc] initWithRequest:rssRequest delegate:delegate startImmediately:YES];
    return connect;
}
@end
