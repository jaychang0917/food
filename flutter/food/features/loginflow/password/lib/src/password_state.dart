import 'package:base/base.dart';
import 'package:loginflow/loginflow.dart';

@immutable
class PasswordState extends Equatable {
  const PasswordState({
    required this.loginFlowData,
    this.navigation,
    this.isLoadingVisible = false,
    this.isNextButtonEnabled = true,
    this.isNextButtonVisible = false,
    this.isErrorViewVisible = false
  });

  final LoginFlowData loginFlowData;

  final Navigation? navigation;

  final bool isLoadingVisible;

  final bool isNextButtonEnabled;

  final bool isNextButtonVisible;

  final bool isErrorViewVisible;

  PasswordState copyWith({
    LoginFlowData? loginFlowData,
    Navigation? navigation,
    bool? isLoadingVisible,
    bool? isNextButtonEnabled,
    bool? isNextButtonVisible,
    bool? isErrorViewVisible
  }) {
    return PasswordState(
      loginFlowData: loginFlowData ?? this.loginFlowData,
      navigation: navigation ?? this.navigation,
      isLoadingVisible: isLoadingVisible ?? this.isLoadingVisible,
      isNextButtonEnabled: isNextButtonEnabled ?? this.isNextButtonEnabled,
      isNextButtonVisible: isNextButtonVisible ?? this.isNextButtonVisible,
      isErrorViewVisible: isErrorViewVisible ?? this.isErrorViewVisible,
    );
  }

  @override
  List<Object?> get props => [
    loginFlowData,
    navigation,
    isLoadingVisible,
    isNextButtonEnabled,
    isNextButtonVisible,
    isErrorViewVisible
  ];
}

abstract class Navigation {}

class MainNavigation extends Navigation {}

