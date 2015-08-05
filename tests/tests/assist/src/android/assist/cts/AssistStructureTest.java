/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.assist.cts;

import android.assist.TestStartActivity;
import android.assist.common.Utils;

import android.app.assist.AssistContent;
import android.app.assist.AssistStructure;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import java.lang.Exception;
import java.lang.Override;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 *  Test that the AssistStructure returned is properly formatted.
 */

public class AssistStructureTest extends AssistTestBase {
    static final String TAG = "AssistStructureTest";

    private static final String TEST_CASE_TYPE = "ASSIST_STRUCTURE";

    public AssistStructureTest() {
        super();
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        startTestActivity(TEST_CASE_TYPE);
        waitForBroadcast(Utils.TestCaseType.ASSIST_STRUCTURE);
    }

    public void testAssistStructure() throws Exception {
        // verify assist data contains what we want.
        // go through all things in the bundle, verify not null, verify contains what we want.

        // TODO(awlee): verify that the context is not off by default.
        if (mAssistContent == null || mAssistBundle == null) {
            fail("Received null assistBundle or assistContent.");
            return;
        }

        if (mAssistStructure == null) {
            fail("Received null assistStructure");
            return;
        } else {
            verifyAssistStructure(new ComponentName("android.assist.service",
                    "android.assist." + Utils.getTestActivity(TEST_CASE_TYPE)), false /*FLAG_SECURE set*/);
        }
    }

    private void verifyAssistStructure(ComponentName backgroundApp,
            boolean isSecureWindow) {
        // Check component name matches
        assertEquals(backgroundApp.flattenToString(),
                mAssistStructure.getActivityComponent().flattenToString());

        int numWindows = mAssistStructure.getWindowNodeCount();
        assertEquals(1, numWindows);
        for (int i = 0; i < numWindows; i++) {
            AssistStructure.ViewNode node = mAssistStructure.getWindowNodeAt(i).getRootViewNode();
        }
    }
}