import 'package:base/base.dart';

@immutable
class LandingState extends Equatable {
  LandingState({
    this.country,
    this.navigation
  });

  final Country? country;

  final Navigation? navigation;

  LandingState copyWith({
    Country? country,
    Navigation? navigation
  }) {
    return LandingState(
      country: country ?? this.country,
      navigation: navigation ?? this.navigation,
    );
  }

  @override
  List<Object?> get props => [country, navigation];
}

abstract class Navigation {}

class LoginFlowNavigation extends Navigation {}
