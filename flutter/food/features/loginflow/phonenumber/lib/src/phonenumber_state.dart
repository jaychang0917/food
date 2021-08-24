import 'package:base/base.dart';
import 'package:loginflow/loginflow.dart';

@immutable
class PhoneNumberState extends Equatable {
  const PhoneNumberState({
    required this.loginFlowData,
    this.countryCode = '-',
    this.countryFlag,
    this.navigation,
    this.isLoadingVisible = false,
    this.isNextButtonEnabled = true,
    this.isNextButtonVisible = false,
    this.isErrorViewVisible = false
  });

  final LoginFlowData loginFlowData;

  final String countryCode;

  final String? countryFlag;

  final Navigation? navigation;

  final bool isLoadingVisible;

  final bool isNextButtonEnabled;

  final bool isNextButtonVisible;

  final bool isErrorViewVisible;

  PhoneNumberState copyWith({
    LoginFlowData? loginFlowData,
    String? countryCode,
    String? countryFlag,
    Navigation? navigation,
    bool? isLoadingVisible,
    bool? isNextButtonEnabled,
    bool? isNextButtonVisible,
    bool? isErrorViewVisible
  }) {
    return PhoneNumberState(
      loginFlowData: loginFlowData ?? this.loginFlowData,
      countryCode: countryCode ?? this.countryCode,
      countryFlag: countryFlag ?? this.countryFlag,
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
    countryCode,
    countryFlag,
    navigation,
    isLoadingVisible,
    isNextButtonEnabled,
    isNextButtonVisible,
    isErrorViewVisible
  ];
}

abstract class Navigation {}

class CountrySelectionNavigation extends Navigation {}

class PasswordNavigation extends Navigation {}