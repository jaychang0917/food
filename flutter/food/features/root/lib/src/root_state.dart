import 'package:base/base.dart';

@immutable
class RootState extends Equatable {
  const RootState({
    this.navigation,
    this.error
  });

  final Navigation? navigation;

  final Exception? error;

  RootState copyWith({
    Navigation? navigation,
    Exception? error
  }) {
    return RootState(
      navigation: navigation ?? this.navigation,
      error: error ?? this.error,
    );
  }

  @override
  List<Object?> get props => [navigation, error];
}

abstract class Navigation {}

class HomeNavigation extends Navigation {}

class LandingNavigation extends Navigation {}
