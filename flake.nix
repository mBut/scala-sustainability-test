{
  inputs = {
    nixpkgs.url = "github:nixos/nixpkgs";
  };

  outputs = { self, nixpkgs, flake-utils }:
    let
      system = "x86_64-linux";
      pkgs = nixpkgs.legacyPackages.${system};
    in {
      devShell.${system} = pkgs.mkShell {
        buildInputs = (with pkgs; [
          linuxKernel.packages.linux_5_15.perf
          mill
        ]);
      };
    };
}
