import 'package:base/base.dart';

@immutable
class LoginFlowData extends Equatable {
  const LoginFlowData({
    this.phoneNumber = '',
    this.password = ''
  });

  final String phoneNumber;
  final String password;

  LoginFlowData copyWith({
    String? phoneNumber,
    String? password,
  }) {
    return LoginFlowData(
      phoneNumber: phoneNumber ?? this.phoneNumber,
      password: password ?? this.password,
    );
  }

  @override
  List<Object?> get props => [phoneNumber, password];
}