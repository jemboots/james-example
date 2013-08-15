//
//  RssData.h
//  iPhoneRssReader
//
//  Created by James on 8/15/13.
//  Copyright (c) 2013 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface RssData : NSObject
@property (retain, nonatomic) NSString *title;
@property (retain, nonatomic) NSString *link;
@property (retain, nonatomic) NSString *content;
@property (retain, nonatomic) NSString *publishDate;

@end
