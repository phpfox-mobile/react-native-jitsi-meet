//
//  ViewController.m
//  JitsiMobile
//
//  Created by Sébastien Krafft on 07/08/2018.
//  Copyright © 2018 Sébastien Krafft. All rights reserved.
//

#import "JitsiMeetViewController.h"

@implementation JitsiMeetViewController

- (void)viewDidLoad {
    [super viewDidLoad];
}

- (void) setDelegate:(id<JitsiMeetViewDelegate>) delegate {
    JitsiMeetView *jitsiMeetView = (JitsiMeetView *) self.view;
    if (delegate) {
        jitsiMeetView.delegate = delegate;
    }
}

- (BOOL)checkKey:(NSString *)key options:(NSDictionary *)options
{
    id obj = options[key];
    if (obj != nil) {
        if (obj != (id)[NSNull null]) {
            return [[options objectForKey:key] boolValue];
        }
        else {
            return NO;
        }
    }
    else {
        return NO;
    }
}

- (void)call:(NSString *) url options:(NSDictionary *)options {
    JitsiMeetView *jitsiMeetView = (JitsiMeetView *) self.view;
    JitsiMeetConferenceOptions *optionsBuilder = [JitsiMeetConferenceOptions fromBuilder:^(JitsiMeetConferenceOptionsBuilder *builder) {        builder.room = url;
        builder.videoMuted = [self checkKey:@"videoMuted" options:options];
    }];
    [jitsiMeetView join:optionsBuilder];
}


- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


@end
