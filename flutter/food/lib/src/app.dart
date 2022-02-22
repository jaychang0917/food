import 'package:base/base.dart';
import 'package:country/country.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:landing/landing.dart';
import 'package:main/main.dart';
import 'package:password/password.dart';
import 'package:phonenumber/phonenumber.dart';
import 'package:root/root.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';

class App extends StatelessWidget {
  const App({
    Key? key,
    required sessionRepository,
    required countryRepository,
    required userRepository
  }) : _sessionRepository = sessionRepository,
       _countryRepository = countryRepository,
       _userRepository = userRepository,
       super(key: key);

  final SessionRepository _sessionRepository;

  final CountryRepository _countryRepository;

  final UserRepository _userRepository;

  @override
  Widget build(BuildContext context) {
    return MultiRepositoryProvider(
        providers: [
          RepositoryProvider.value(value: _sessionRepository),
          RepositoryProvider.value(value: _countryRepository),
          RepositoryProvider.value(value: _userRepository),
        ],
        child: MaterialApp(
            localizationsDelegates: AppLocalizations.localizationsDelegates,
            supportedLocales: AppLocalizations.supportedLocales,
            // todo page transition
            onGenerateRoute: (settings) {
              return MaterialPageRoute(
                settings: settings,
                builder: (_) {
                  // Wrap with Scaffold so that page is able to show banner.
                  return Scaffold(
                    // Wrap with SafeArea so that page is not overlapped with system insets (e.g. status bar).
                    body: SafeArea(
                      child: Container(
                        color: FoodColors.surface,
                        child: _page(settings.name)
                      ),
                    )
                  );
                }
              );
            }
        )
    );
  }

  Widget _page(String? name) {
    if (name == AppRoutes.root) return const RootPage();
    if (name == AppRoutes.landing) return const LandingPage();
    if (name == AppRoutes.loginFlow) return const PhoneNumberPage();
    if (name == AppRoutes.phoneNumber) return const PhoneNumberPage();
    if (name == AppRoutes.password) return const PasswordPage();
    if (name == AppRoutes.countrySelection) return CountrySelectionPage();
    if (name == AppRoutes.main) return const MainPage(activeTab: AppTab.home);
    if (name == AppRoutes.home) return const MainPage(activeTab: AppTab.home);
    if (name == AppRoutes.search) return const MainPage(activeTab: AppTab.search);
    if (name == AppRoutes.order) return const MainPage(activeTab: AppTab.order);
    if (name == AppRoutes.profile) return const MainPage(activeTab: AppTab.profile);
    throw Exception("No such page: $name.");
  }
}
