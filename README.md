Support for Liquid Galaxy Activities
====================================

Java package: com.endpoint.lg.support

Support library with classes used by other Liquid Galaxy Interactive Spaces activities.


Copyright (C) 2013-2014 Google Inc.
Copyright (C) 2015 End Point Corporation

Licensed under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License. You may obtain a copy of
the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
License for the specific language governing permissions and limitations under
the License.

Unique Build Information
========================
This activity is unique because it is a "library" type activity. It does not actually do anything itself, and it is not deployed an Interactive Spaces controller. Instead, once built, this activity should be copied into the "bootstrap" directory of your local controller, making the library available while building other activites which depend upon it.
