import 'package:api/api.dart';
import 'package:data/data.dart';
import 'package:flutter/material.dart';

import 'src/app.dart';

void main() {
  runApp(App(
    sessionRepository: SessionRepository(sessionApi: sessionApi, sessionManager: SessionManager()),
    countryRepository: CountryRepository(configApi: configApi),
    userRepository: UserRepository(userApi: userApi),
  ));
}
