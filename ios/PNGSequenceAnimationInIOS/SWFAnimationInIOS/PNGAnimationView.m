//
//  SpriteSheetView.m
//  SpriteSheetView
//
//  Created by James on 12/17/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "PNGAnimationView.h"

@implementation PNGAnimationView
@synthesize isPlaying;
@synthesize totalFrame;
@synthesize isLoop;

-(id)initWithFrame:(CGRect)frame fileNamePattern:(NSString *)fileNamePattern startFrame:(int)startFrame totalFrame:(int)totalFrames interval:(double)timeInterval
{
    self = [super initWithFrame:frame];
    
    isLoop = NO;
    frameSpeed = timeInterval;
    currentImageIndex = 0;
    spriteSheetImage = [NSMutableArray array];
    totalFrame = totalFrames;
    
    NSString *imageName;    
    for (int i = startFrame; i <= totalFrames; i++)
    {
        if(i < 10)
        {
            imageName = [NSString stringWithFormat:@"000%d", i];
        }
        else if(i<100)
        {
            imageName = [NSString stringWithFormat:@"00%d", i];
        }
        else if(i<1000)
        {
            imageName = [NSString stringWithFormat:@"0%d", i];
        }
        else if(i<1000)
        {
            imageName = [NSString stringWithFormat:@"%d", i];
        }
        
        imageName = [NSString stringWithFormat:fileNamePattern, imageName];
        [spriteSheetImage addObject:[UIImage imageNamed:imageName]];
    }
    
    return self;
}

-(void)doAnimation
{
    if(currentImageIndex < spriteSheetImage.count)
    {
        self.image = [spriteSheetImage objectAtIndex:currentImageIndex];
        currentImageIndex++;
    }
    else
    {
        currentImageIndex = 0;
        
        if(!isLoop)
        {
            [timer invalidate];
            isPlaying = NO;
        }
    }
}

-(void)playAnimation
{
    if(!isPlaying)
    {
        isPlaying = YES;
        timer = [NSTimer scheduledTimerWithTimeInterval:frameSpeed target:self selector:@selector(doAnimation) userInfo:nil repeats:YES];
    }
}

-(void)stopAnimation
{
    if(isPlaying)
    {
        [timer invalidate];
        isPlaying = NO;
    }
}

-(void)gotoFrame:(int)frame
{
    currentImageIndex = frame - 1;
    self.image = [spriteSheetImage objectAtIndex:currentImageIndex];
}

-(void)clearMomery
{
    [spriteSheetImage removeAllObjects];
    spriteSheetImage = nil;
    
    if(timer)
    {
        [timer invalidate];
    }
}
@end
