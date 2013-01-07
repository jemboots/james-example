//
//  RssHttpController.h
//  iPhoneRssReader
//
//  Created by James on 12/15/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface RssHttpController : NSObject

-(NSURLConnection *) getRSSContent: (NSString *) pageID rssurl: (NSString *) url delegate: (id <NSURLConnectionDataDelegate>) delegate;

@end
