import os
import subprocess
import shutil

def convert_svg_to_png():
    """
    Converts SVG files starting with 'face-' in the current directory to PNGs,
    then moves them to '../public/assets/'.
    """
    current_directory = "."
    output_directory = "../public/assets/"

    # Ensure the output directory exists
    os.makedirs(output_directory, exist_ok=True)

    for svg_name in os.listdir(current_directory):
        if svg_name.endswith(".svg") and svg_name.startswith("face-"):
            svg_path = os.path.join(current_directory, svg_name)
            print(f"Exporting {svg_name}...")

            # Get the base name without the extension
            svg_base_name = os.path.splitext(svg_name)[0]
            png_dest_name = f"{svg_base_name}.png"
            temp_png_path = os.path.join(current_directory, png_dest_name)
            final_png_path = os.path.join(output_directory, png_dest_name)

            # Convert SVG to PNG using Inkscape
            try:
                subprocess.run(
                    ["inkscape", svg_path, "-C", "-d", "192", "-o", temp_png_path],
                    check=True
                )
            except subprocess.CalledProcessError as e:
                print(f"Error converting {svg_name}: {e}")
                continue

            # Move the generated PNG to the destination
            try:
                shutil.move(temp_png_path, final_png_path)
                print(f"Moved {png_dest_name} to {output_directory}")
            except FileNotFoundError:
                print(f"Error: {temp_png_path} not found after conversion.")
            except Exception as e:
                print(f"Error moving {png_dest_name}: {e}")

if __name__ == "__main__":
    convert_svg_to_png()