// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.Set;

public final class FindMeetingQuery {
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
      List<String> meetingAttendees = request.getAttendees().stream().collect(Collectors.toList());
      long meetingDuration = request.getDuration();

      // Find conflicting time ranges
      List<TimeRange> conflictingTimeRanges =  getConflictingTimeRanges(events, meetingAttendees);
      
      // Find meeting time ranges
      List<TimeRange> meetingTimeRanges = getMeetingTimeRanges(conflictingTimeRanges, meetingDuration);

      return meetingTimeRanges;
  }

  // Function to return list of conflicting time slots
  private List<TimeRange> getConflictingTimeRanges(Collection<Event> events, List<String> meetingAttendees){

      List<TimeRange> conflictingTimeRanges = new ArrayList<>();
      for(Event event:events){
          Set<String> eventAttendees = event.getAttendees();
          if ((eventAttendees.stream().filter(meetingAttendees::contains).collect(Collectors.toList())).size() > 0){
              conflictingTimeRanges.add(event.getWhen());
          }
      }
      return conflictingTimeRanges;
  }
  
  // Function to return possible meeting time slots
  private List<TimeRange> getMeetingTimeRanges(List<TimeRange> conflictingTimeRanges, long meetingDuration){

      // Sort conflicting time ranges according to increasing start time
      conflictingTimeRanges.sort(TimeRange.ORDER_BY_START);

      List<TimeRange> meetingTimeRanges = new ArrayList<>();

      int prevTimeRangeEnd = 0;
      for(TimeRange timeRange:conflictingTimeRanges){
          int timeRangeStart = timeRange.start();
          int timeRangeEnd = timeRange.end();
          int duration = timeRangeStart - prevTimeRangeEnd;
          if (duration >= meetingDuration){
              meetingTimeRanges.add(TimeRange.fromStartDuration(prevTimeRangeEnd,duration));
          }

          if (timeRangeEnd > prevTimeRangeEnd)
            prevTimeRangeEnd = timeRangeEnd;
      }

      // For last time range and END_OF_DAY
      int duration = (24*60) - prevTimeRangeEnd;
      if (duration >= meetingDuration){
          meetingTimeRanges.add(TimeRange.fromStartDuration(prevTimeRangeEnd,duration));
      }

      return meetingTimeRanges;
  }
}
